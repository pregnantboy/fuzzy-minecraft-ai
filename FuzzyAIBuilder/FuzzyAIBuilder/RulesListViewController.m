//
//  RulesListViewController.m
//  FuzzyAIBuilder
//
//  Created by Ian Chen on 25/1/16.
//  Copyright © 2016 Ian Chen. All rights reserved.
//

#import "RulesListViewController.h"
#import "MainMenuViewController.h"
#import "RuleTableViewCell.h"
#import "RuleNumTableViewCell.h"

@interface RulesListViewController ()

@end

@implementation RulesListViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do view setup here.
    [self.background setAlphaValue:0.9];
    [self.view addSubview:self.background positioned:NSWindowBelow relativeTo:nil];
    [MainMenuViewController addShadow:self.backButton];
    [MainMenuViewController addShadow:self.modifyButton];
    [MainMenuViewController addShadow:self.saveButton];
    [MainMenuViewController addShadow:self.moveDownButton];
    [MainMenuViewController addShadow:self.addNewButton];
    [MainMenuViewController addShadow:self.moveUpButton];
    
    [MainMenuViewController addWhiteMenuText:self.modifyButton withSize:22 withText:@"Modify"];
    [MainMenuViewController addWhiteMenuText:self.saveButton withSize:22 withText:@"Save"];
    
    [self.backButton setAction:@selector(goToMainMenu)];
    [self.background setTarget:self];
    [self.addNewButton setAction:
     @selector(goToEditorPage)];
}

- (void)viewDidAppear {
    [super viewDidAppear];
    NSButton *bgButton = [[NSButton alloc] initWithFrame:self.view.frame];
    [bgButton setTransparent:YES];
    [self.background addSubview:bgButton];
    [bgButton setAction:@selector(clearSelection)];
}

#pragma mark - NSTableView delegates

- (BOOL)tableView:(NSTableView *)aTableView shouldSelectRow:(NSInteger)rowIndex {
    if (rowIndex >= 0) {
        [self.modifyButton setEnabled:YES];
    }
    return YES;
}

- (NSInteger)numberOfRowsInTableView:(NSTableView *)tableView {
    return 7;
}

- (CGFloat)tableView:(NSTableView *)tableView
         heightOfRow:(NSInteger)row {
    return 50;
}

- (NSView *)tableView:(NSTableView *)tableView
   viewForTableColumn:(NSTableColumn *)tableColumn
                  row:(NSInteger)row {
    
    // Retrieve to get the @"MyView" from the pool or,
    // if no version is available in the pool, load the Interface Builder version
    if ([tableColumn.identifier isEqual: @"priority"]) {
        RuleNumTableViewCell *result = (RuleNumTableViewCell*)[tableView makeViewWithIdentifier:@"RuleNumTableViewCell" owner:self];
        [result setNum:row];
        return result;
    }
    
    RuleTableViewCell *result = (RuleTableViewCell *) [tableView makeViewWithIdentifier:@"RuleTableViewCell" owner:self];
    [result setRuleString:@"IF condition IS something AND condition IS NOT something THEN do this action."];
    return result;
}

#pragma mark - IBActions

- (IBAction)goToMainMenu {
    NSStoryboard *mainsb = [NSStoryboard storyboardWithName:@"Main" bundle:[NSBundle mainBundle]];
    NSViewController *mainMenuVc = [mainsb instantiateControllerWithIdentifier:@"MainMenuViewController"];
    [[mainMenuVc view] setFrame:self.view.frame];
    [[NSApp mainWindow] setContentViewController:mainMenuVc];
}

- (IBAction)goToEditorPage {
    NSStoryboard *mainsb = [NSStoryboard storyboardWithName:@"Main" bundle:[NSBundle mainBundle]];
    NSViewController *vc = [mainsb instantiateControllerWithIdentifier:@"EditorViewController"];
    [[vc view] setFrame:self.view.frame];
    [[NSApp mainWindow] setContentViewController:vc];
}

- (void)clearSelection {
    [self.tableView deselectAll: nil];
    [self.modifyButton setEnabled:NO];
}

@end
