//
//  ExportViewController.m
//  FuzzyAIBuilder
//
//  Created by Ian Chen on 31/1/16.
//  Copyright © 2016 Ian Chen. All rights reserved.
//

#import "ExportViewController.h"
#import "MainMenuViewController.h"

@interface ExportViewController () {
    AIObject *AI;
    NSString *fileName;
    NSURL *fileUrl;
}

@end

@implementation ExportViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    [self.background setAlphaValue:0.90];
    [self.backButton setAction:@selector(goToSelectAIPage)];
    [MainMenuViewController addWhiteMenuText:self.slot1 withSize:24 withText:@"Slot 1"];
    [MainMenuViewController addWhiteMenuText:self.slot2 withSize:24 withText:@"Slot 2"];
    [MainMenuViewController addWhiteMenuText:self.slot3 withSize:24 withText:@"Slot 3"];
    [MainMenuViewController addWhiteMenuText:self.slot4 withSize:24 withText:@"Slot 4"];
    fileUrl = [[NSURL URLWithString:PROJECT_DIR] URLByDeletingLastPathComponent];
    fileUrl = [fileUrl URLByAppendingPathComponent:@"/forge/src/main/java/fuzzyMod/fuzzyLogic/fcl"];
    [self.slot1 setAction:@selector(saveToSlot:)];
    [self.slot2 setAction:@selector(saveToSlot:)];
    [self.slot3 setAction:@selector(saveToSlot:)];
    [self.slot4 setAction:@selector(saveToSlot:)];
}

- (void)loadAI:(AIObject *)ai {
    AI = ai;
    [self.name setStringValue:[NSString stringWithFormat:@"Exporting %@", [AI getName]]];
}

#pragma mark - IBAction

- (void)goToSelectAIPage {
    NSStoryboard *mainsb = [NSStoryboard storyboardWithName:@"Main" bundle:[NSBundle mainBundle]];
    NSViewController *vc = [mainsb instantiateControllerWithIdentifier:@"SelectAIViewController"];
    [[vc view] setFrame:self.view.frame];
    [[NSApp mainWindow] setContentViewController:vc];
}

- (IBAction)saveToSlot:(id)sender {
    if (sender == self.slot1) {
        [self confirmSaveToSlot:1 from:sender];
    }
    if (sender == self.slot2) {
        [self confirmSaveToSlot:2 from:sender];

    }
    if (sender == self.slot3) {
        [self confirmSaveToSlot:3 from:sender];

    }
    if (sender == self.slot4) {
        [self confirmSaveToSlot:4 from:sender];

    }
}

- (BOOL)exportToSlot:(NSInteger)slotNo from:(id)sender {
    NSString *filePath = [[fileUrl URLByAppendingPathComponent:[NSString stringWithFormat:@"slot%ld.fcl",(long)slotNo]]absoluteString];
    NSMutableString *stringToWrite = [[NSMutableString alloc] init];
    for (int i = 0; i < [AI getNumRules]; i ++ ) {
        Rule *r = [AI getRuleAtIndex:i];
        if ([r isComplete]) {
            [stringToWrite appendString:[NSString stringWithFormat:@"\tRULE %d : ", i]];
            [stringToWrite appendString:[NSString stringWithFormat:@"%@\r\n",[r getExportRuleString]]];
        }
    }
    [stringToWrite appendString:@"END_RULEBLOCK\n\nEND_FUNCTION_BLOCK"];
    [[NSFileManager defaultManager] createFileAtPath:filePath contents:nil attributes:nil];
    NSError *error;
    [stringToWrite writeToFile:filePath atomically:YES encoding:NSUTF8StringEncoding error:&error];
    if (!error) {
        [sender setEnabled:NO];
        [MainMenuViewController addWhiteMenuText:sender withSize:24 withText:@"Exported"];
        return YES;
    } else {
        NSAlert *alert = [[NSAlert alloc] init];
        [alert setMessageText:[NSString stringWithFormat:@"Error saving: %@", error]];
        [alert addButtonWithTitle:@"OK"];
        [alert runModal];
        return NO;
    }
}

- (void)confirmSaveToSlot:(NSInteger)slotNo from:(id)sender {
    NSAlert *alert = [[NSAlert alloc] init];
    [alert setMessageText:[NSString stringWithFormat:@"Confirm Exporting of AI: %@ \nto Slot:%ld", [AI getName],slotNo]];
    [alert setInformativeText:@"This will overwrite previously saved AI."];
    [alert addButtonWithTitle:@"Yes"];
    [alert addButtonWithTitle:@"No"];
    [alert setIcon:[NSImage imageNamed:@"icon"]];
    [alert beginSheetModalForWindow:self.view.window completionHandler:^(NSModalResponse returnCode) {
        if(returnCode == NSAlertFirstButtonReturn)
        {
            [self exportToSlot:slotNo from:sender];
        } else {
            return;
        }
    }];
}

@end
