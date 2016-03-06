//
//  SelectAIViewController.m
//  FuzzyAIBuilder
//
//  Created by Ian Chen on 30/1/16.
//  Copyright © 2016 Ian Chen. All rights reserved.
//

#import "SelectAIViewController.h"
#import "MainMenuViewController.h"
#import "AIDatabase.h"
#import "AIObject.h"
#import "RulesListViewController.h"
#import "ExportViewController.h"

@interface SelectAIViewController () {
    NSMutableArray *AIarray;
}

@end

@implementation SelectAIViewController

- (void)viewDidLoad {
    [super viewDidLoad];

    [self.background setAlphaValue:0.90];
    AIarray = [AIDatabase loadData];
    [self.backButton setAction:@selector(goToMainPage)];
    [MainMenuViewController addWhiteMenuText:self.modifyButton withSize:22 withText:@"Modify"];
    [MainMenuViewController addWhiteMenuText:self.deleteButton withSize:22 withText:@"Delete"];
    [MainMenuViewController addWhiteMenuText:self.exportButton withSize:22 withText:@"Export"];
    [self.modifyButton setAction:@selector (goToRulesListPage)];
    [self.deleteButton setAction:@selector (showAlertToDelete)];
    [self.exportButton setAction:@selector (goToExportPage)];
}

- (void)viewDidAppear {
    [super viewDidAppear];
    NSButton *bgButton = [[NSButton alloc] initWithFrame:self.view.frame];
    [bgButton setTransparent:YES];
    [self.background addSubview:bgButton];
    [bgButton setAction:@selector(clearSelection)];
}

#pragma mark - TableView delegates
- (NSInteger)numberOfRowsInTableView:(NSTableView *)tableView {
    return [AIarray count];
}

- (CGFloat)tableView:(NSTableView *)tableView
         heightOfRow:(NSInteger)row {
    return 50;
}

- (NSView *)tableView:(NSTableView *)tableView
   viewForTableColumn:(NSTableColumn *)tableColumn
                  row:(NSInteger)row {
    NSTableCellView *result = (NSTableCellView *) [tableView makeViewWithIdentifier:@"textCell" owner:self];
    result.textField.stringValue = [[self getAIObjectAtIndex:row] getName];
    return result;
}

- (void)tableViewSelectionDidChange:(NSNotification *)aNotification {
    if ([self.tableView selectedRow] < 0) {
        [self.modifyButton setEnabled:NO];
        [self.deleteButton setEnabled:NO];
        [self.exportButton setEnabled:NO];
    } else {
        [self.modifyButton setEnabled:YES];
        [self.deleteButton setEnabled:YES];
        [self.exportButton setEnabled:YES];
    }
}

- (AIObject *)getAIObjectAtIndex:(NSInteger)index {
    if (index < [AIarray count]) {
        AIObject *ai = (AIObject *)[AIarray objectAtIndex:index];
        return ai;
    } else {
        return nil;
    }
}

#pragma mark - IBAction

- (void)goToRulesListPage {
    NSStoryboard *mainsb = [NSStoryboard storyboardWithName:@"Main" bundle:[NSBundle mainBundle]];
    RulesListViewController *vc = (RulesListViewController*)[mainsb instantiateControllerWithIdentifier:@"RulesListViewController"];
    [[vc view] setFrame:self.view.frame];
    [[NSApp mainWindow] setContentViewController:vc];
    [vc loadAI:AIarray atIndex:[self.tableView selectedRow]];
}

- (void)clearSelection {
    [self.tableView deselectAll: nil];
}


- (IBAction)goToMainPage {
    NSStoryboard *mainsb = [NSStoryboard storyboardWithName:@"Main" bundle:[NSBundle mainBundle]];
    NSViewController *mainMenuVc = [mainsb instantiateControllerWithIdentifier:@"MainMenuViewController"];
    [[mainMenuVc view] setFrame:self.view.frame];
    [[NSApp mainWindow] setContentViewController:mainMenuVc];
}

- (void)goToExportPage {
    NSStoryboard *mainsb = [NSStoryboard storyboardWithName:@"Main" bundle:[NSBundle mainBundle]];
    ExportViewController *vc = (ExportViewController*)[mainsb instantiateControllerWithIdentifier:@"ExportViewController"];
    [[vc view] setFrame:self.view.frame];
    [[NSApp mainWindow] setContentViewController:vc];
    [vc loadAI:[AIarray objectAtIndex:[self.tableView selectedRow]]];
}

- (void)deleteAIObject {
    [AIarray removeObjectAtIndex:[self.tableView selectedRow]];
    [AIDatabase saveData:AIarray];
    [self clearSelection];
}

- (void)showAlertToDelete {
    NSAlert *alert = [[NSAlert alloc] init];
    [alert setMessageText:[NSString stringWithFormat:@"Confirm Deletion of AI: %@", [[self getAIObjectAtIndex:[self.tableView selectedRow]] getName]]];
    [alert setInformativeText:@"This cannot be undone."];
    [alert addButtonWithTitle:@"Yes"];
    [alert addButtonWithTitle:@"No"];
    [alert setIcon:[NSImage imageNamed:@"icon"]];
    [alert beginSheetModalForWindow:self.view.window completionHandler:^(NSModalResponse returnCode) {
        if(returnCode == NSAlertFirstButtonReturn)
        {
            [self deleteAIObject];
            [self.tableView reloadData];
        }
    }];
}


@end
